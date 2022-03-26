package co.farooq.premissionhandlingcompose

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import co.farooq.premissionhandlingcompose.ui.theme.PremissionHandlingComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PremissionHandlingComposeTheme {
                val permissionState = rememberMultiplePermissionsState(
                    permissions = listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                )

                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(key1 = lifecycleOwner, effect = {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            permissionState.launchMultiplePermissionRequest()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                })

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    permissionState.permissions.forEach { param ->
                        when (param.permission) {
                            Manifest.permission.CAMERA -> {
                                when {
                                    param.isGranted() -> {
                                        Text(text = "Camera Permission Accepted")
                                    }
                                    param.showRational() -> {
                                        Text(text = "Camera Permission Need Permissions for Camera to Work")
                                    }
                                    param.isPermanentlyDenied() -> {
                                        Text(text = "Camera Permission is Permanently Denied")
                                    }
                                }
                            }
                            Manifest.permission.RECORD_AUDIO -> {
                                when {
                                    param.isGranted() -> {
                                        Text(text = "Record Audio Permission Accepted")
                                    }
                                    param.showRational() -> {
                                        Text(text = "Record Audio Need Permissions for Record your voice")
                                    }
                                    param.isPermanentlyDenied() -> {
                                        Text(text = "Record Audio Permission is Permanently Denied")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}