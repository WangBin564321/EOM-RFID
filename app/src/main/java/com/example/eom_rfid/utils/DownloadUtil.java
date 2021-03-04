package com.example.eom_rfid.utils;

/**
 * Description:
 * Author:bwang
 * Date:2020/12/9 9:26
 */
public class DownloadUtil {



//    public void downloadFile(Activity activity, String dirName, String fileName, String urlStr) throws MalformedURLException {
//        EasyPermissions.requestPermissions(activity, "", 1
//                , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
//                });
//        URL url = new URL(urlStr);
//
//        final long startTime = System.currentTimeMillis();
//        Log.i("DOWNLOAD", "startTime=" + startTime);
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        Request request = new Request.Builder().url(url).build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                // 下载失败
//                e.printStackTrace();
//                Log.i("DOWNLOAD", "download failed");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                // 储存下载文件的目录
//
//                try {
//                    is = response.body().byteStream();
//                    long total = response.body().contentLength();
//                    File file = createFile(dirName, fileName);
//                    fos = new FileOutputStream(file);
//                    long sum = 0;
//                    while ((len = is.read(buf)) != -1) {
//                        fos.write(buf, 0, len);
//                        sum += len;
//                        int progress = (int) (sum * 1.0f / total * 100);
//                        // 下载中
////                        listener.onDownloading(progress);
//                        Log.d("HTAG", "onResponse==========>: " + progress);
//                    }
//                    fos.flush();
//                    // 下载完成
////                    listener.onDownloadSuccess();
//                    Log.i("DOWNLOAD", "download success");
//                    Log.i("DOWNLOAD", "totalTime=" + (System.currentTimeMillis() - startTime));
//                } catch (Exception e) {
//                    e.printStackTrace();
////                    listener.onDownloadFailed();
//                    Log.i("DOWNLOAD", "download failed");
//                } finally {
//                    try {
//                        if (is != null)
//                            is.close();
//                    } catch (IOException e) {
//                    }
//                    try {
//                        if (fos != null)
//                            fos.close();
//                    } catch (IOException e) {
//                    }
//                }
//            }
//        });
//    }


}
