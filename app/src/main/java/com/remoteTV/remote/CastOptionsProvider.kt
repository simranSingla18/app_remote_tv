package com.remoteTV.remote

import android.content.Context
import com.google.android.gms.cast.CastMediaControlIntent
import com.google.android.gms.cast.framework.CastOptions
import com.google.android.gms.cast.framework.OptionsProvider
import com.google.android.gms.cast.framework.SessionProvider

@Suppress("UNUSED")
class CastOptionsProvider : OptionsProvider {

    /** Sample uses default receiver application Id.
     *
     *  If you want to customize receiver device you need to register at Developer Console and
     *  replace the following [CastOptions.Builder.setReceiverApplicationId] with yours.
     *
     */
    /* companion object {
         const val CUSTOM_NAMESPACE = "urn:x-cast:custom_namespace"
     }*/

    override fun getCastOptions(context: Context): CastOptions {

        /**
         * This will also show a notification in device.
         */


        return CastOptions.Builder()
            .setReceiverApplicationId(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID)
            .build()
    }

    override fun getAdditionalSessionProviders(p0: Context): MutableList<SessionProvider>? {
        return null
    }
}