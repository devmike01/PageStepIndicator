PageStepIndicator was built from existing [StepIndicator](https://github.com/layerlre/StepIndicator) library developed by [Layerlre](https://github.com/layerlre) . I needed a page indicator that can display title and at the same time have a transparent border, but I couldn't find one. So I modified the existing StepIndicator's source code to suit my needs, and shamelessly published it as a new library. 

[![](https://jitpack.io/v/devmike01/PageStepIndicator.svg)](https://jitpack.io/#devmike01/PageStepIndicator)

### Preview
<img src="https://raw.githubusercontent.com/devmike01/PageStepIndicator/master/preview_01.gif" width="240" height="400" />

### How To Use 

- Add `PageStepIndicator` to your app.

 Add it to your root build.gradle at the end of repositories:

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

 Add the dependency

```groovy
dependencies {
	        implementation 'com.github.devmike01:pagestepindicator:1.1.1'
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

- Create a pager adapter by extending `FragmentStatePagerAdapter` or `FragmentPagerAdapter`.

- Setup the adapter with your viewpager.
 
- Call `setupWithViewPager(ViewPager)` from `PageStepIndicator` and pass the instance of your viewpager as an argument to it.

That's all. You can customize it the way you want.

### Customization
- `pgStepColor` Color of the step indicator
- `pgCurrentStepColor` Color of the current step
- `pgBackgroundColor` Background color of the step indicator
- `pgTextColor` Active step position color
- `pgSecondaryTextColor` Inactive step position color
- `pgRadius` Radius of the step indicator
- `pgStrokeWidth` Stroke Width of a current step
- `pgStepCount` Size of step (With out ViewPager)
- `pgTitles` Title of pages
- `pgActiveTitleColor` Current color of page's title
- `pgInActiveTitleColor` Color of your previous or future page's title
- `pgTitleTextSize` Size of your page's title
- `pgLineHeight` Height of indicator line
- `pgStrokeAlpha` Opacity of current stroke(255 means the color is solid)



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
