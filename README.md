# Network Monitor

A simple library to monitor network state changes.

Thanks to !(Now in Android)[https://github.com/android/nowinandroid] project for the inspiration.

## Usage

Get the network instance
```kotlin
val networkInstance = NetworkMonitor.getInstance(context)


val networkStateFlow: Flow<NetworkState> = networkMonitor.networkState()
val networkState: NetworkState = networkMonitor.networkStateSync()
```

## Network changes

There are two ways to Listen to network state changes.

#### Synchronous

```kotlin
val networkState: NetworkState = networkMonitor.networkStateSync()
```

#### Asynchronous

```kotlin
val networkStateFlow: Flow<NetworkState> = networkMonitor.networkState()
```

---

#### SSID changes

Listen to Wi-Fi SSID changes

```kotlin
val ssidFlow: Flow<String> = networkMonitor.ssidChanges()
```

---

## License

```
   Copyright 2023 M S Lalith

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
