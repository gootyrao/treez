package course.labs.graphicslab

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.random.Random.Default.nextInt

class BubbleActivity : Activity() {

    // The Main view
    private lateinit var mFrame: RelativeLayout

    // Bubble image's bitmap
    private lateinit var mBitmap: Bitmap

    // Display dimensions
    private var mDisplayWidth: Int = 0
    private var mDisplayHeight: Int = 0

    // Sound variables

    // AudioManager
    private var mAudioManager: AudioManager? = null

    // SoundPool
    private var mSoundPool: SoundPool? = null

    // ID for the bubble popping sound
    private var mSoundID: Int = 0

    // Audio volume
    private var mStreamVolume: Float = 0.toFloat()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        // Set up user interface
        mFrame = findViewById<View>(R.id.frame) as RelativeLayout

        // Load basic bubble Bitmap
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.b64)

    }

    override fun onResume() {
        super.onResume()

        // Manage bubble popping sound
        // Use AudioManager.STREAM_MUSIC as stream type

        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        mStreamVolume = mAudioManager!!
            .getStreamVolume(AudioManager.STREAM_MUSIC)
            .toFloat() / mAudioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        val musicAttribute = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        mSoundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(musicAttribute)
            .build()

        mSoundID = mSoundPool?.load(this, R.raw.bubble_pop, 1)!!

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {

            // Get the size of the display so this View knows where borders are
            mDisplayWidth = mFrame.width
            mDisplayHeight = mFrame.height

        }
    }

    override fun onPause() {

        mSoundPool?.let {
            it.unload(mSoundID)
            it.release()
        }

        mSoundPool = null

        super.onPause()
    }

// BubbleView is a View that displays a bubble.
// This class handles animating, drawing, and popping amongst other actions.
// A new BubbleView is created for each bubble on the display

    inner class BubbleView internal constructor(context: Context, x: Float, y: Float) :
        View(context) {
        private val mPainter = Paint()
        private var mMoverFuture: ScheduledFuture<*>? = null
        private var mScaledBitmapWidth: Int = 0
        private var mScaledBitmap: Bitmap? = null

        // location, speed and direction of the bubble
        private var mXPos: Float = 0.toFloat()
        private var mYPos: Float = 0.toFloat()
        private var mDx: Float = 0.toFloat()
        private var mDy: Float = 0.toFloat()
        private val mRadius: Float
        private val mRadiusSquared: Float
        private var mRotate: Long = 0
        private var mDRotate: Long = 0

        // [DONE] TODO - Return true if the BubbleView is still on the screen after
        // the move operation
        private val isOutOfView: Boolean
            get() = !(-mScaledBitmapWidth <= mYPos && mYPos <= mDisplayHeight + mScaledBitmapWidth
                    && -mScaledBitmapWidth <= mXPos && mXPos <= mDisplayHeight + mScaledBitmapWidth)


        init {
            Log.i(TAG, "Creating Bubble at: x:$x y:$y")

            // Create a new random number generator to
            // randomize size, rotation, speed and direction
            val r = Random()

            // Creates the bubble bitmap for this BubbleView
            createScaledBitmap(r)

            // Radius of the Bitmap
            mRadius = (mScaledBitmapWidth / 2).toFloat()
            mRadiusSquared = mRadius * mRadius

            // Adjust position to center the bubble under user's finger
            mXPos = x - mRadius
            mYPos = y - mRadius

            // Set the BubbleView's speed and direction
            setSpeedAndDirection(r)

            // Set the BubbleView's rotation
            setRotation(r)

            mPainter.isAntiAlias = true

        }

        private fun setRotation(r: Random) {

            mDRotate = if (speedMode == RANDOM) {

                // [DONE] TODO - set rotation randomly in range [1..3]
                (1..3).random().toLong()

            } else {
                0
            }
        }

        private fun setSpeedAndDirection(r: Random) {

            // Used by test cases
            when (speedMode) {
                SINGLE -> {
                    mDx = 40f
                    mDy = 40f
                }
                STILL -> {
                    // No speed
                    mDx = 0f
                    mDy = 0f
                }
                else -> {

                    // [DONE] TODO - Set movement direction and speed
                    // Limit movement speed in the x and y
                    // direction to [-3..3] pixels per movement.
                    mDx = (-3..3).random().toFloat()
                    mDy = (-3..3).random().toFloat()
                    Log.i(TAG, "Bubble x speed:")
                    Log.i(TAG, mDx.toString())
                    Log.i(TAG, "Bubble y speed:")
                    Log.i(TAG, mDy.toString())


                }
            }
        }

        private fun createScaledBitmap(r: Random) {

            mScaledBitmapWidth = if (speedMode != RANDOM) {
                BITMAP_SIZE * 3
            } else {

                // [DONE] TODO - set scaled bitmap size in range [1..3] * BITMAP_SIZE
                (1..3).random() * BITMAP_SIZE

            }

            // [DONE] TODO - create the scaled bitmap using size set above
//            mScaledBitmap = createBitmap(mBitmap, mXPos.toInt(), mYPos.toInt(),
//                mScaledBitmapWidth, mScaledBitmapWidth)
            mScaledBitmap = Bitmap.createScaledBitmap(mBitmap, mScaledBitmapWidth,
                mScaledBitmapWidth, false) // [2nd option for bitmap constructor]
        }

        // Start moving the BubbleView & updating the display
        fun start() {

            // Creates a WorkerThread
            val executor = Executors
                .newScheduledThreadPool(1)

            // Execute the run() in Worker Thread every REFRESH_RATE
            // milliseconds. Save reference to this job in mMoverFuture

            mMoverFuture = executor.scheduleWithFixedDelay({
                // [DONE] TODO - implement movement logic.
                // Each time this method is run the BubbleView should
                // move one step. If the BubbleView exits the display,
                // stop the BubbleView's Worker Thread.
                // Otherwise, ensure that the BubbleView will be redrawn.
                Log.i(TAG, "Just refreshed, about to draw")
                if (!this.moveWhileOnScreen()) {
                    // stop worker thread for BubbleView
                    stop(false)
                }

                this.postInvalidate()

            }, 0, REFRESH_RATE.toLong(), TimeUnit.MILLISECONDS)
        }

        // Cancel the Bubble's movement
        // Remove Bubble from mFrame
        // Play pop sound if the BubbleView was popped

        internal fun stop(wasPopped: Boolean) {

            if (null != mMoverFuture) {
                if (!mMoverFuture!!.isDone) {
                    mMoverFuture!!.cancel(true)
                }

                // This work will be performed on the UI Thread
                mFrame.post {
                    // [DONE] TODO - Remove the BubbleView from mFrame
                    mFrame.removeView(this)

                    // If the bubble was popped by user,
                    // play the popping sound
                    if (wasPopped) {
                        Log.i(TAG, "Pop!")
                        mSoundPool?.play(
                            mSoundID, mStreamVolume,
                            mStreamVolume, 1, 0, 1.0f
                        )
                    }
                    Log.i(TAG, "Bubble removed from view!")
                }
            }
        }

        // Draw the Bubble at its current location
        @Synchronized
        override fun onDraw(canvas: Canvas) {

            // [DONE] TODO - save the canvas
            canvas.save()

            // [DONE] TODO - increase the rotation of the original image by mDRotate
            // is this changing the 'original image'?
            mRotate += mDRotate

            // [DONE] TODO Rotate the canvas by current rotation
            // Hint - Rotate around the bubble's center, not its position
            canvas.rotate(mRotate.toFloat(), mXPos + mRadius, mYPos + mRadius)

            // [DONE] TODO - draw the bitmap at it's new location
            // Do I use the canvas to draw this?
            canvas.drawBitmap(mScaledBitmap!!, mXPos, mYPos, mPainter)

            // [DONE] TODO - restore the canvas
            canvas.restore()

        }

        // Returns true if the BubbleView is still on the screen after the move
        // operation
        @Synchronized
        private fun moveWhileOnScreen(): Boolean {

            // [DONE] TODO - Move the BubbleView
            mXPos += mDx
            mYPos += mDy

            Log.i(TAG, "New x pos after move op:")
            Log.i(TAG, mXPos.toString())
            Log.i(TAG, "New y pos after move op:")
            Log.i(TAG, mYPos.toString())

            Log.i(TAG, "isOutOfView:")
            Log.i(TAG, isOutOfView.toString())

            return !isOutOfView

        }

    }

    override fun onBackPressed() {
        openOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // [DONE] TODO - Handle addition and deletion of bubbles *from options menu* (bubble handles deletion off of edge)
        // Added bubbles should be given random locations.
        // The bubble to delete is the most recently added bubble
        // that is still in the frame.

        // Hint: You can get all Views in mFrame using the
        // ViewGroup.getChildCount() method
        when (item.itemId) {
            R.id.menu_add_bubble -> {
                // [DONE] TODO (above continued) - handle addition of bubbles here

                var randX = (0..mDisplayWidth).random().toFloat()
                var randY = (0..mDisplayHeight).random().toFloat()

                var newBubble = BubbleView(applicationContext, randX, randY)
                mFrame.addView(newBubble)
                newBubble.start() // added while debugging, not confident

                return true
            }
            R.id.menu_delete_bubble -> {
                // [DONE] TODO (above continued) - handle deletion of bubbles here

                if (mFrame.childCount > 0) {
                    val mostRecentBub = mFrame.getChildAt(mFrame.childCount - 1) as BubbleView
                    Log.i(TAG, "Bubble to delete:")
                    Log.i(TAG, mostRecentBub.toString())

                    mostRecentBub.stop(true)
                }

                return true
            }
            R.id.menu_still_mode -> {
                speedMode = STILL
                return true
            }
            R.id.menu_single_speed -> {
                speedMode = SINGLE
                return true
            }
            R.id.menu_random_mode -> {
                speedMode = RANDOM
                return true
            }
            R.id.quit -> {
                exitRequested()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun exitRequested() {
        super.onBackPressed()
    }

    companion object {

        // These variables are for testing purposes, do not modify
        private const val RANDOM = 0
        private const val SINGLE = 1
        private const val STILL = 2
        private var speedMode = RANDOM
        private const val BITMAP_SIZE = 64
        private const val REFRESH_RATE = 40
        private const val TAG = "Lab-Graphics"
    }
}