# RIG – Random Image Generator
A library for Android that generates random Bitmap images based on the passed parameters like width, height, type of generator, etc. Generated images can be also automatically saved in the requested path with appropriate quality.


<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/1.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/2.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/3.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/4.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/5.png" width="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/6.png" width="128">

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
For more information, please see javadoc.
### Embed generators
There are currently 5 generators, and 2 "wrapper" generators.

- FlatColorGenerator

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/FlatColorGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/FlatColorGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/FlatColorGenerator/3.png" height="128">

- ColoredPixelsGenerator

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredPixelsGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredPixelsGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredPixelsGenerator/3.png" height="128">

- ColoredRectangleGenerator

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredRectangleGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredRectangleGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredRectangleGenerator/3.png" height="128">

- ColoredNoiseGenerator

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredNoiseGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredNoiseGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredNoiseGenerator/3.png" height="128">

- ColoredCirclesGenerator

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredCirclesGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredCirclesGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/ColoredCirclesGenerator/3.png" height="128">

- MirroredGenerator (to create mirrored effect for generated images)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/MirroredGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/MirroredGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/MirroredGenerator/3.png" height="128">

- TextOverlayGenerator (to draw text in front of generated image)

<img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/TextOverlayGenerator/1.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/TextOverlayGenerator/2.png" height="128"> <img src="https://github.com/stedi-akk/RandomImageGenerator/raw/master/images/TextOverlayGenerator/3.png" height="128">

Additional generators are planned in the future.
### Custom generators
To create your own generator, you just need to implement Generator interface:
```java
public class MyGenerator implements Generator {
    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        // your generation here
    }
}
```

### Download
Gradle:
```groovy
compile 'com.stedi.randomimagegenerator:rig:1.0.0'
```
This library is synchronous, and you should not use it in the main thread.  
   
There is also a sample application available in the /sample directory.

### License
> Copyright 2017 Dima Stepanchenko
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
