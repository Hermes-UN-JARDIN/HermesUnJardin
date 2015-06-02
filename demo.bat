adb shell "su -c 'mkdir /data/data/com.hermes_un_jardin.hermesunjardin/files'"
adb shell "su -c 'chmod -R 777 /data/data/com.hermes_un_jardin.hermesunjardin'"

adb push .\app\src\main\res\assets /data/data/com.hermes_un_jardin.hermesunjardin/files