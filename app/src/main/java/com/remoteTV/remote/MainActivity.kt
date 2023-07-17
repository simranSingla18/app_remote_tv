package com.remoteTV.remote

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.mediarouter.media.MediaControlIntent
import androidx.mediarouter.media.MediaRouteSelector
import androidx.mediarouter.media.MediaRouter
import com.example.remote.databinding.ActivityMainBinding
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.SessionManager
import com.google.android.gms.cast.framework.SessionManagerListener


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mediaRouter: MediaRouter? = null
    private var mediaRouteSelector: MediaRouteSelector? = null
    lateinit var castContext: CastContext
    var castSession: CastSession? = null
    var value = true
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        castContext = CastContext.getSharedInstance(this)
        sessionManager = castContext.sessionManager

        CastButtonFactory.setUpMediaRouteButton(applicationContext, binding.tvCast)

        mediaRouter = MediaRouter.getInstance(this)
        mediaRouteSelector = MediaRouteSelector.Builder()
            .addControlCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK)
            .addControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO)
            .addControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO)
            .build()

        //MediaControlIntent.CATEGORY_REMOTE_PLAYBACK, mediaRouterCallback)


        binding.ivMoveBack.setOnClickListener {
            play()
        }

        binding.ivPause.setOnClickListener {
            pause()
        }

    }

    fun play() {
        if (castSession != null) {
            val remoteMediaClient = castSession!!.remoteMediaClient
            remoteMediaClient?.play()
        }
    }

    fun pause() {
        if (castSession != null) {
            val remoteMediaClient = castSession!!.remoteMediaClient
            remoteMediaClient?.pause()
        }
    }

    private val mediaRouterCallback = object : MediaRouter.Callback() {
        override fun onRouteSelected(router: MediaRouter, route: MediaRouter.RouteInfo) {
            super.onRouteSelected(router, route)
            if (sessionManager.currentSession != null)
                castSession =
                    CastContext.getSharedInstance(applicationContext).sessionManager.currentCastSession!!
        }

        override fun onRouteUnselected(router: MediaRouter, route: MediaRouter.RouteInfo) {
            super.onRouteUnselected(router, route)
            castSession = null
        }
    }


    override fun onResume() {
        super.onResume()
        sessionManager.addSessionManagerListener(sessionManagerListener, CastSession::class.java)
        mediaRouter?.addCallback(
            mediaRouteSelector!!,
            mediaRouterCallback,
            MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY
        )

    }

    override fun onPause() {
        super.onPause()
        sessionManager.removeSessionManagerListener(sessionManagerListener, CastSession::class.java)
    }

    private val sessionManagerListener = object : SessionManagerListener<CastSession> {
        override fun onSessionStarting(session: CastSession) {
            castSession = session

            Toast.makeText(this@MainActivity, "on", Toast.LENGTH_LONG).show()
        }

        override fun onSessionStarted(session: CastSession, sessionId: String) {
            Toast.makeText(this@MainActivity, "off", Toast.LENGTH_LONG).show()
        }

        override fun onSessionStartFailed(session: CastSession, error: Int) {}
        override fun onSessionEnding(session: CastSession) {}
        override fun onSessionEnded(session: CastSession, error: Int) {

        }

        override fun onSessionResuming(session: CastSession, sessionId: String) {}
        override fun onSessionResumed(session: CastSession, wasSuspended: Boolean) {
            castSession = session

        }

        override fun onSessionResumeFailed(session: CastSession, error: Int) {}
        override fun onSessionSuspended(session: CastSession, reason: Int) {}
    }
}

/* setUpCastListener()
 castContext = CastContext.getSharedInstance(this)


    mediaRouter = MediaRouter.getInstance(this)
        mediaRouteSelector = MediaRouteSelector.Builder()
            .addControlCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK)
            .addControlCategory(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID)
            .addControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO)
            .addControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO)
            .build()
        mediaRouterCallback = object : MediaRouter.Callback() {

            override fun onRouteSelected(
                router: MediaRouter,
                route: MediaRouter.RouteInfo,
                reason: Int
            ) {
                val name = router.routes
                Toast.makeText(this@MainActivity, name[0].name, Toast.LENGTH_SHORT).show()
            }

            override fun onRouteUnselected(
                router: MediaRouter,
                route: MediaRouter.RouteInfo,
                reason: Int
            ) {
            }

            override fun onRouteAdded(router: MediaRouter, route: MediaRouter.RouteInfo) {
                //val ro= router.routes
            }

            override fun onRouteRemoved(router: MediaRouter, route: MediaRouter.RouteInfo) {
            }

            override fun onRouteChanged(router: MediaRouter, route: MediaRouter.RouteInfo) {
            }


            override fun onRouteVolumeChanged(router: MediaRouter, route: MediaRouter.RouteInfo) {
            }

        }

        // Get the MediaRouteButton from your layout
        binding.tvCast.routeSelector = mediaRouteSelector!!;


    override fun onStart() {
        super.onStart();
        // Add media router callback when the activity starts
        mediaRouterCallback?.let {
            mediaRouteSelector?.let { it1 ->
                mediaRouter?.addCallback(
                    it1, it,
                    MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        castContext.sessionManager.startSession()
        castContext?.sessionManager?.addSessionManagerListener(sessionManagerListener, CastSession::class.java)
    }
    override fun onStop() {
        super.onStop();
        // Remove media router callback when the activity stops
        mediaRouterCallback?.let { mediaRouter?.removeCallback(it) }
    }


*/