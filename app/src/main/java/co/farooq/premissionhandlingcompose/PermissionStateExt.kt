package co.farooq.premissionhandlingcompose

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied() : Boolean {
    return !this.status.isGranted && !this.status.shouldShowRationale
}

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isGranted() : Boolean {
    return this.status.isGranted
}

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.showRational() : Boolean {
    return this.status.shouldShowRationale
}