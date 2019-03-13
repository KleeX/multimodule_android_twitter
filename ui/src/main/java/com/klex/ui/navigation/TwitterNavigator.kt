package com.klex.ui.navigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

@Navigator.Name("keep_state_fragment") // `keep_state_fragment` is used in navigation xml
class KeepStateNavigator(
    private val context: Context,
    private val manager: FragmentManager, // Should pass childFragmentManager.
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()
        navOptions?.let {
            transaction.setCustomAnimations(
                it.enterAnim,
                it.exitAnim,
                it.popEnterAnim,
                it.popExitAnim
            )
        }
        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }

        var fragment = manager.findFragmentByTag(tag)
        if (fragment == null) {
            val className = destination.className
            fragment = instantiateFragment(context, manager, className, args)
            transaction.add(containerId, fragment, tag)
            if (manager.fragments.size > 0)
                transaction.addToBackStack(tag)
        } else {
            transaction.attach(fragment)
        }
        transaction.commit()

        return null
    }
}

class TwitterNavHostFragment : NavHostFragment() {
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return KeepStateNavigator(requireContext(), childFragmentManager, id)
    }
}