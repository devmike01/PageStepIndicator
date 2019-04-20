This app was made from existing PageIndicator library. I wanted to use a page indicator that could display title at the same time, but I couldn't find one. So I came up with this one and decided to make into a library.

### How To Use 

- Add `PageStepIndicator` to your app.

##### Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

##### Add the dependency

```groovy
dependencies {
	        implementation 'com.github.devmike01:PageStepIndicator:1.0.0'
	}
```

- Add `PageStepIndicator` to your layout. E.g:

```xml
    <devmike.jade.com.PageStepIndicator
            app:pgTitles="@array/titles"
            app:pgTitleTextSize="15sp"
            app:pgStrokeAlpha="255"
            app:pgRadius="15dp"
            app:pgCurrentStepColor="@android:color/holo_red_dark"
            app:pgTextColor="@android:color/white"
            app:pgStepColor="@android:color/holo_purple"
            android:id="@+id/page_stepper"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
```

- Setup a pager adapter

- Add the adapter to the PageStepIndicator.

That's all. You can customize it the way you want.

License
-------

    Copyright 2018 Oladipupo Gbenga

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
