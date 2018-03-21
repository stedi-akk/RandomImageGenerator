# RIG – Random Image Generator
A library for Android that generates random [Bitmap](https://developer.android.com/reference/android/graphics/Bitmap.html) images. Generation is performed on the basis of different passed parameters like width, height, type of generator, color palette, etc. Images can be also automatically saved in the requested path with appropriate quality.


<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/2.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/1.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/4.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/9.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/5.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/6.png" width="128">
<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/11.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/8.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/3.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/10.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/7.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/12.png" width="128">

## Usage
Simple example with callback:
```java
new Rig.Builder()
        .setGenerator(generator)
        .setFixedSize(1920, 1080) // can be also 1x1, 1x1000, etc
        .setCount(10)
        .setCallback(new GenerateCallback() {
            @Override
            public void onGenerated(ImageParams imageParams, Bitmap bitmap) {
                // will be called 10 times with a new bitmap 1920x1080 on every call
            }
            @Override
            public void onFailedToGenerate(ImageParams imageParams, Exception e) {
                // in case if generation failed
            }
        })
        .build()
        .generate();
```
Simple example with compression:
```java
new Rig.Builder()
        .setGenerator(generator)
        .setFixedSize(1920, 1080) // can be also 666x666, 1337x1337, etc
        .setQuality(Quality.jpg(90))
        .setFileSavePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath())
        .setFileSaveCallback(new SaveCallback() {
            @Override
            public void onSaved(Bitmap bitmap, File file) {
                // will be called when bitmap is saved
            }
            @Override
            public void onFailedToSave(Bitmap bitmap, Exception e) {
                // in case if compression failed
            }
        })
        .build()
        .generate();
```
For more information, please see [Rig.Builder](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/Rig.java#L323) javadoc.
### Embed generators
There are currently 6 generators, and 3 effects.

- [FlatColorGenerator](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/FlatColorGenerator.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/FlatColorGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/FlatColorGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/FlatColorGenerator/3.png" height="128">

- [ColoredPixelsGenerator](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/ColoredPixelsGenerator.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredPixelsGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredPixelsGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredPixelsGenerator/3.png" height="128">

- [ColoredRectangleGenerator](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/ColoredRectangleGenerator.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredRectangleGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredRectangleGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredRectangleGenerator/3.png" height="128">

- [ColoredNoiseGenerator](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/ColoredNoiseGenerator.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredNoiseGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredNoiseGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredNoiseGenerator/3.png" height="128">

- [ColoredCirclesGenerator](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/ColoredCirclesGenerator.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredCirclesGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredCirclesGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredCirclesGenerator/3.png" height="128">

- [ColoredLinesGenerator](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/ColoredLinesGenerator.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredLinesGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredLinesGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredLinesGenerator/3.png" height="128">

- [MirroredEffect](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/effects/MirroredEffect.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/MirroredEffect/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/MirroredEffect/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/MirroredEffect/3.png" height="128">

- [ThresholdEffect](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/effects/ThresholdEffect.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ThresholdEffect/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ThresholdEffect/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ThresholdEffect/3.png" height="128">

- [TextOverlayEffect](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/effects/TextOverlayEffect.java)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/TextOverlayEffect/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/TextOverlayEffect/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/TextOverlayEffect/3.png" height="128">

### Custom generators
To create your own generator, you just need to implement [Generator](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/generators/Generator.java) interface:
```java
public class MyGenerator implements Generator {
    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        // your generation here
    }
}
```

### Color palette
By default, the library generates images with all available [HSV](https://en.wikipedia.org/wiki/HSL_and_HSV) colors. [RigPalette](https://github.com/stedi-akk/RandomImageGenerator/blob/master/library/src/main/java/com/stedi/randomimagegenerator/RigPalette.java) class is used to change this palette.

Examples:

```java
RigPalette.blackAndWhite()
```
<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Grayscale/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Grayscale/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Grayscale/3.png" height="128">

```java
RigPalette.fromColor(Color.RED)
```
<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Red/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Red/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Red/3.png" height="128">

```java
RigPalette.fromColor(Color.GREEN)
```
<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Green/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Green/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Green/3.png" height="128">

```java
RigPalette.fromColor(Color.BLUE)
```
<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Blue/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Blue/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/Blue/3.png" height="128">

## Download
Gradle:
```groovy
compile 'com.stedi.randomimagegenerator:rig:1.1.0'
```
   
There is also a sample application available in the [/sample](https://github.com/stedi-akk/RandomImageGenerator/tree/master/sample) directory.

## License
> Copyright 2018 Dima Stepanchenko
> 
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
> 
>    http://www.apache.org/licenses/LICENSE-2.0
> 
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
