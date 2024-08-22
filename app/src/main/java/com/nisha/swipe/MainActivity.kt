package com.nisha.swipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.nisha.swipe.ui.adapter.PagerAdapter
import com.nisha.swipe.databinding.ActivityMainBinding
import com.nisha.swipe.ui.MiddleFragment
import com.nisha.swipe.ui.LeftSwipeFragment
import com.nisha.swipe.ui.RightSwipeFragment
import java.util.LinkedList
import java.util.Queue

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val middleFragment by lazy { MiddleFragment() }
    private var isScrollStart = false
    private var isLike = false
    private var isPass = false

    /**
     * A testing queue of image URLs to display. Replace afterwards
     */
    private val testingImagesUrl: Queue<String> by lazy {
        LinkedList<String>().also {
            it.push("https://assets.teenvogue.com/photos/65f70886e0c291c70b616650/1:1/w_960,c_limit/2090709677")
            it.push("https://stat5.bollywoodhungama.in/wp-content/uploads/2024/02/Deepika-Padukone-spills-her-hair-secrets-reveals-what-kinds-of-hairstyles-she-prefers-watch-2.jpg")
            it.push("https://source.boomplaymusic.com/buzzgroup2/M00/38/5F/rBEe_GKzAjGAFaDAAAMScME856s057.jpg")
            it.push("https://cdn.britannica.com/66/251066-050-A318AF98/dakota-johnson-met-gala.jpg")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    /**
     * Initializes the ViewPager and sets up the page change listener.
     */
    private fun init() {
        val pagerAdapter = PagerAdapter(this)
        // swipe left fragment
        pagerAdapter.addScreenFragment(LeftSwipeFragment())
        // display user info fragment
        pagerAdapter.addScreenFragment(middleFragment)
        // swipe right fragment
        pagerAdapter.addScreenFragment(RightSwipeFragment())

        binding.viewpager.adapter = pagerAdapter

        //forcefully set the current item to 1 to display user info fragment
        binding.viewpager.currentItem = 1

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (testingImagesUrl.isNotEmpty())
                    middleFragment.setImage(testingImagesUrl.element())
                if (position == 1 && positionOffset == 0f) {
                    isScrollStart = true
                    rotation(0f)
                    if (isLike) resetDetails(true)
                    else if (isPass) resetDetails(false)
                    isLike = false
                    isPass = false
                }
                if (isScrollStart) {
                    if (position == 1 && positionOffsetPixels == 0) {
                        rotation(0f)
                        middleFragment.resetView()
                    } else {
                        middleFragment.setSwipeView()
                        if (position == 0) {
                            if (positionOffset <= 0.95) {
                                isPass = true
                                isLike = false
                                middleFragment.displayPassMark()
                            }
                            rotation(3f)
                        } else if (position == 1) {
                            if (positionOffset >= 0.05) {
                                isLike = true
                                isPass = false
                                middleFragment.displayLikeMark()
                            }
                            rotation(-3f)
                        }
                    }
                }
                binding.viewpager.currentItem = 1
            }
        })
    }

    /**
     * Resets the view and updates the screen based on whether the user liked or passed.
     *
     * @param isLike True if the user liked the item, false if they passed.
     */
    private fun resetDetails(isLike: Boolean) {
        println(isLike)
        binding.viewpager.currentItem = 1
        middleFragment.resetView()
        // todo : update screen & do the other job
        if (testingImagesUrl.isNotEmpty()) {
            testingImagesUrl.remove()
            visibility(testingImagesUrl.isNotEmpty())
        }
    }

    /**
     * Rotates the ViewPager.
     *
     * @param rotation The rotation angle in degrees.
     */
    private fun rotation(rotation: Float) {
        binding.viewpager.rotation = rotation
    }

    /**
     * Sets the visibility of the ViewPager based on whether there are more images to display.
     *
     * @param isVisible True to show the ViewPager, false to hide it.
     */
    private fun visibility(isVisible: Boolean) {
        if (isVisible) middleFragment.setImage(testingImagesUrl.element())
        binding.viewpager.isVisible = isVisible
    }
}