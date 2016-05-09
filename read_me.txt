I developed this using Eclipse, but I also tested it with javac compiler.
To Compile and run, just follow this steps:
1 - Open the Terminal
2 - Navigate to the BTCode folder
3 - Run the following command line:
  a - javac -d . src/bt/*.java
  b - This will put the bt package in the BTCode folder (if you want to put somewhere else, just change the “.” after -d for the path you want to)
4 - Run the system:
  a - java bt.BtMain “fileNameWithExtention” “package1” …
  b - Example: java bt.BtMain ../testFileOtherDir.txt gui test runner

