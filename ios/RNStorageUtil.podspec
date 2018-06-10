
Pod::Spec.new do |s|
  s.name         = "RNStorageUtil"
  s.version      = "1.0.0"
  s.summary      = "RNStorageUtil"
  s.description  = <<-DESC
                  Info about free space and external devices, only for **Android**
                   DESC
  s.homepage     = "https://github.com/jerson/react-native-storage-util"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "jeral17@gmail.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/jerson/react-native-storage-util.git", :tag => "master" }
  s.source_files  = "RNStorageUtil/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  