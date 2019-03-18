package com.klex.ui.compose

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.klex.presentation.compose.ComposePresenter
import com.klex.presentation.compose.ComposeView
import com.klex.ui.R
import com.klex.ui.createImageFile
import com.klex.ui.fromUrl
import com.klex.ui.mvpx.MvpXFragment
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_compose.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ComposeFragment : MvpXFragment(), ComposeView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ComposePresenter

    @ProvidePresenter
    fun providePresenter(): ComposePresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_compose, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_post.setOnClickListener {
            presenter.text = et_tweet_text.text.toString()
            presenter.pendingPost()
        }
        iv_add_photo.setOnClickListener {
            showChooseDialog()
        }
    }

    private fun showChooseDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.choose_source))
        val sources = arrayOf(getString(R.string.gallery), getString(R.string.camera))
        builder.setItems(
            sources
        ) { _, which ->
            when (which) {
                PHOTO_GALLERY_INDEX -> presenter.searchPhoto()
                PHOTO_CAMERA_INDEX -> presenter.takePhoto()
            }
        }
        builder.create().show()
    }

    override fun openGallery() {
        if (!requestReadWritePermissions()) {
            presenter.functionCompletePermissions = { openGallery() }
            return
        }
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun openCamera() {
        if (!requestReadWritePermissions()) {
            presenter.functionCompletePermissions = { openCamera() }
            return
        }
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            val photoFile = requireContext().createImageFile()
            presenter.currentPhotoPath = photoFile.absolutePath
            val photoURI =
                FileProvider.getUriForFile(requireContext(), "com.klex.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
        }
    }

    override fun pendingSuccess() {
        activity?.onBackPressed()
    }

    override fun validationError() {
        Toast.makeText(context, getString(R.string.error_tweet_empty), Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) return
                presenter.currentPhotoPath?.let {
                    cropImage(it)
                }
            }
            GALLERY_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) return
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data?.data)
                val path = saveImage(bitmap)
                cropImage(path)
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) return
                val file = File(CropImage.getActivityResult(data).uri.path).absolutePath
                presenter.selectedFilePath = file
            }
        }
    }

    private fun cropImage(path: String) {
        CropImage.activity(Uri.fromFile(File(path)))
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1)
            .start(requireContext(), this)
    }

    private fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val file = requireContext().createImageFile()
        val fo = FileOutputStream(file)
        fo.write(bytes.toByteArray())
        MediaScannerConnection.scanFile(
            requireContext(),
            arrayOf(file.path),
            arrayOf("image/jpeg"), null
        )
        fo.close()
        Log.d("file", "File Saved::--->" + file.absolutePath)
        return file.absolutePath
    }

    override fun setPicture(path: String) {
        iv_photo.fromUrl(path)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE ->
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    presenter.functionCompletePermissions?.invoke()
                }
        }
    }

    private fun requestReadWritePermissions() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_CODE
            )
            false
        } else true

    companion object {
        private const val CAMERA_REQUEST_CODE = 0x10
        private const val GALLERY_REQUEST_CODE = 0x11
        private const val PERMISSIONS_REQUEST_CODE = 0x12
        private const val PHOTO_GALLERY_INDEX = 0
        private const val PHOTO_CAMERA_INDEX = 1
    }
}