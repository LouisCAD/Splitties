/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package xyz.louiscad.splittiessample.extensions

import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.*
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.fingerprint.FingerprintManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.midi.MidiManager
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.print.PrintManager
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager

inline val Context.windowManager get() = getSystemService(WINDOW_SERVICE) as WindowManager
inline val Context.layoutInflater get() = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
inline val Context.activityManager get() = getSystemService(ACTIVITY_SERVICE) as ActivityManager
inline val Context.powerManager get() = getSystemService(POWER_SERVICE) as PowerManager
inline val Context.alarmManager get() = getSystemService(ALARM_SERVICE) as AlarmManager
inline val Context.notificationManager get() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
inline val Context.keyguardManager get() = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
inline val Context.locationManager get() = getSystemService(LOCATION_SERVICE) as LocationManager
inline val Context.searchManager get() = getSystemService(SEARCH_SERVICE) as SearchManager
inline val Context.sensorManager get() = getSystemService(SENSOR_SERVICE) as SensorManager
inline val Context.storageManager get() = getSystemService(STORAGE_SERVICE) as StorageManager
inline val Context.vibrator get() = getSystemService(VIBRATOR_SERVICE) as Vibrator
inline val Context.connectivityManager get() = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
inline val Context.wifiManager get() = getSystemService(WIFI_SERVICE) as WifiManager
inline val Context.wifiP2pManager get() = getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
inline val Context.audioManager get() = getSystemService(AUDIO_SERVICE) as AudioManager
inline val Context.telephonyManager get() = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
inline val Context.inputMethodManager get() = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
inline val Context.downloadManager get() = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
inline val Context.uiModeManager get() = getSystemService(UI_MODE_SERVICE) as UiModeManager
inline val Context.usbManager get() = getSystemService(USB_SERVICE) as UsbManager
inline val Context.nfcManager get() = getSystemService(NFC_SERVICE) as NfcManager
inline val Context.devicePolicyManager get() = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
inline val Context.appWidgetManager get() = getSystemService(APPWIDGET_SERVICE) as AppWidgetManager
inline val Context.textServicesManager get() = getSystemService(TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager
inline val Context.clipboardManager get() = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
inline val Context.accessibilityManager get() = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
inline val Context.accountManager get() = getSystemService(ACCOUNT_SERVICE) as AccountManager
inline val Context.dropBoxManager get() = getSystemService(DROPBOX_SERVICE) as DropBoxManager

// System Services below require an API higher than 15
inline val Context.nsdManager get() = getSystemService(NSD_SERVICE) as NsdManager
inline val Context.mediaRouter get() = getSystemService(MEDIA_ROUTER_SERVICE) as MediaRouter
inline val Context.inputManager get() = getSystemService(INPUT_SERVICE) as InputManager
inline val Context.displayManager get() = getSystemService(DISPLAY_SERVICE) as DisplayManager
inline val Context.userManager get() = getSystemService(USER_SERVICE) as UserManager
inline val Context.bluetoothManager get() = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
inline val Context.appOpsManager get() = getSystemService(APP_OPS_SERVICE) as AppOpsManager
inline val Context.printManager get() = getSystemService(PRINT_SERVICE) as PrintManager
inline val Context.consumerIrManager get() = getSystemService(CONSUMER_IR_SERVICE) as ConsumerIrManager
inline val Context.captioningManager get() = getSystemService(CAPTIONING_SERVICE) as CaptioningManager
inline val Context.mediaSessionManager get() = getSystemService(MEDIA_SESSION_SERVICE) as MediaSessionManager
inline val Context.telecomManager get() = getSystemService(TELECOM_SERVICE) as TelecomManager
inline val Context.launcherApps get() = getSystemService(LAUNCHER_APPS_SERVICE) as LauncherApps
inline val Context.restrictionsManager get() = getSystemService(RESTRICTIONS_SERVICE) as RestrictionsManager
inline val Context.cameraManager get() = getSystemService(CAMERA_SERVICE) as CameraManager
inline val Context.tvInputManager get() = getSystemService(TV_INPUT_SERVICE) as TvInputManager
inline val Context.batteryManager get() = getSystemService(BATTERY_SERVICE) as BatteryManager
inline val Context.jobScheduler get() = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
inline val Context.usageStatsManager get() = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
inline val Context.mediaProjectionManager get() = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
inline val Context.subscriptionManager get() = getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
inline val Context.fingerPrintManager get() = getSystemService(FINGERPRINT_SERVICE) as FingerprintManager
inline val Context.networkStatsManager get() = getSystemService(NETWORK_STATS_SERVICE) as NetworkStatsManager
inline val Context.carrierConfigManager get() = getSystemService(CARRIER_CONFIG_SERVICE) as CarrierConfigManager
inline val Context.midiManager get() = getSystemService(MIDI_SERVICE) as MidiManager
inline val Context.hardwarePropertiesManager get() = getSystemService(HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager
inline val Context.systemHealthManager get() = getSystemService(SYSTEM_HEALTH_SERVICE) as SystemHealthManager
