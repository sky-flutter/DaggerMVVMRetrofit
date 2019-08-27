package com.example.daggermvvmretrofit.fragment_helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.daggermvvmretrofit.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FragmentNavigationFactory @Inject constructor(val context: Context) {

    private var fragment: Fragment? = null
    private var tag: String? = null

    fun <T : Fragment> make(aClass: Class<T>): FragmentActionPerformer<T> {
        return make(FragmentFactory.getFragment(aClass))
    }

    fun <T : Fragment> make(fragment: T?): FragmentActionPerformer<T> {
        this.fragment = fragment
        this.tag = fragment!!.javaClass.simpleName
        return Provider(fragment, this)
    }

    private inner class Provider<T : Fragment>
    (private val fragment: T, private val navigationFactory: FragmentNavigationFactory) : FragmentActionPerformer<T> {
        internal var sharedElements: List<Pair<View, String>>? = null

        override fun add(toBackStack: Boolean) {
            openFragment(fragment,
                    Option.ADD, toBackStack, tag!!, sharedElements)
        }

        override fun add(toBackStack: Boolean, tag: String) {
            openFragment(fragment,
                    Option.ADD, toBackStack, tag, sharedElements)
        }

        override fun replace(toBackStack: Boolean) {
            openFragment(fragment,
                    Option.REPLACE, toBackStack, tag!!, sharedElements)
        }

        override fun replace(toBackStack: Boolean, tag: String) {
            openFragment(fragment,
                    Option.REPLACE, toBackStack, tag, sharedElements)
        }


        override fun setBundle(bundle: Bundle): FragmentActionPerformer<*> {
            fragment.arguments = bundle
            return this
        }

        override fun addSharedElements(sharedElements: List<Pair<View, String>>): FragmentActionPerformer<*> {
            this.sharedElements = sharedElements
            return this
        }

        override fun clearHistory(tag: String?): FragmentActionPerformer<*> {
            clearFragmentHistory(tag)
            return this
        }

        override fun hasData(passable: Passable<T>): FragmentActionPerformer<*> {
            passable.passData(fragment)
            return this
        }
    }


    private var intent: Intent? = null
    private var activity: Class<out Activity>? = null
    private var shouldAnimate = true

    fun make(activityClass: Class<out AppCompatActivity>): ActivityBuilder {
        activity = activityClass
        intent = Intent(context, activityClass)
        return Builder()
    }

    private inner class Builder : ActivityBuilder {
        private var bundle: Bundle? = null
        private var activityOptionsBundle: Bundle? = null
        private var isToFinishCurrent: Boolean = false
        private var requestCode: Int = 0

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun start() {
            if (bundle != null)
                intent!!.putExtras(bundle!!)

            if (!shouldAnimate)
                intent!!.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            if (requestCode == 0) {

                if (activityOptionsBundle == null)
                    context.startActivity(intent)
                else
                    context.startActivity(intent, activityOptionsBundle)

            } else {
                val currentFragment = FragmentFactory.getCurrentFragment<Fragment>(context)
                if (currentFragment != null)
                    currentFragment.startActivityForResult(intent, requestCode)
                else
                    (context as AppCompatActivity).startActivityForResult(intent, requestCode)
            }

            /*if (shouldAnimate)
                (context as AppCompatActivity).overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
*/

            if (isToFinishCurrent)
                (context as AppCompatActivity).finish()
        }

        override fun addBundle(bundle: Bundle): ActivityBuilder {
            if (this.bundle != null)
                this.bundle!!.putAll(bundle)
            else
                this.bundle = bundle
            return this
        }

        override fun addSharedElements(pairs: List<Pair<View, String>>): ActivityBuilder {
            val optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(context as AppCompatActivity, *pairs.toTypedArray())
            activityOptionsBundle = optionsCompat.toBundle()
            return this
        }

        override fun byFinishingCurrent(): ActivityBuilder {
            isToFinishCurrent = true
            return this
        }

        override fun byFinishingAll(): ActivityBuilder {
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            return this
        }


        override fun <T : Fragment> setPage(page: Class<T>): ActivityBuilder {
            intent!!.putExtra(ACTIVITY_FIRST_PAGE, page)
            return this
        }

        override fun forResult(requestCode: Int): ActivityBuilder {
            this.requestCode = requestCode
            return this
        }

        override fun shouldAnimate(isAnimate: Boolean): ActivityBuilder {
            shouldAnimate = isAnimate
            return this
        }
    }


    fun openFragment(baseFragment: Fragment, option: Option, isToBackStack: Boolean, tag: String, sharedElements: List<Pair<View, String>>?) {
        val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()


        when (option) {

            Option.ADD -> fragmentTransaction.add(R.id.fl_main_container, baseFragment, tag)
            Option.REPLACE -> fragmentTransaction.replace(R.id.fl_main_container, baseFragment, tag)
            Option.SHOW -> if (baseFragment.isAdded())
                fragmentTransaction.show(baseFragment)
            Option.HIDE -> if (baseFragment.isAdded())
                fragmentTransaction.hide(baseFragment)
        }

        if (isToBackStack)
            fragmentTransaction.addToBackStack(tag)

        // shared element Transition
        /*if (sharedElements != null
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && sharedElements.size() > 0) {

            RootFragment currentFragment = (RootFragment) fragmentManager.findFragmentById(placeHolder);

            Transition changeTransform = TransitionInflater.from(currentFragment.getContext()).
                    inflateTransition(R.transition.change_image_transform);

            currentFragment.setSharedElementReturnTransition(changeTransform);
            // currentFragment.setExitTransition(new Fade());

            baseFragment.setSharedElementEnterTransition(changeTransform);
            //baseFragment.setEnterTransition(new Fade());


            for (Pair<View, String> se : sharedElements) {
                fragmentTransaction.addSharedElement(se.first, se.second);
            }
        }*/

        fragmentTransaction.commitAllowingStateLoss()
    }

    fun showFragment(fragmentToShow: Fragment, vararg fragmentToHide: Fragment) {

        val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        if (fragmentToShow.isAdded) {
            fragmentTransaction.show(fragmentToShow)
        } else
            openFragment(fragmentToShow, Option.ADD, false, "home", null)

        for (fragment in fragmentToHide) {
            if (fragment.isAdded)
                fragmentTransaction.hide(fragment)
        }
        fragmentTransaction.commit()
    }

    fun clearFragmentHistory(tag: String?) {
        sDisableFragmentAnimations = true
        (context as AppCompatActivity).supportFragmentManager.popBackStackImmediate(tag, POP_BACK_STACK_INCLUSIVE)
        sDisableFragmentAnimations = false
    }

    companion object {
        const val ACTIVITY_FIRST_PAGE = "first_page"
        var sDisableFragmentAnimations = false
    }

    enum class Option {
        ADD, REPLACE, SHOW, HIDE
    }
}