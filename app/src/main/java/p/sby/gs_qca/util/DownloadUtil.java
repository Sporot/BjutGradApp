package p.sby.gs_qca.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadUtil {
    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;

    public static DownloadUtil get() { if (downloadUtil == null) { downloadUtil = new DownloadUtil(); } return downloadUtil; }

    private DownloadUtil() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String sessionid, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().addHeader("cookie",sessionid).url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//             下载失败
                listener.onDownloadFailed();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException{
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try{
//                    String iss;
                    is = response.body().byteStream();
                    Log.i("download", "onResponse: "+is);
//                    iss=response.body().string();
                    long total = response.body().contentLength();
                    Log.i("download", "total: "+total);
                    File file = new File(savePath, "hello.doc");
                    System.out.println(savePath);
//                    System.out.println(getNameFromUrl(url));
                    try {
                        fos = new FileOutputStream(file);
                    }catch (Exception e) {
                       System.out.println(e);
                    }
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                    //    int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中 
                    //    listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess();
                }catch (Exception e) {
                    listener.onDownloadFailed();
                }finally {
                    try {
                        if (is != null) is.close();
                    }
                    catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {

                    }
                }
            }
        });
    }
    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */


    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }
    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }



    public interface OnDownloadListener {
        /** * 下载成功 */
        void onDownloadSuccess();
        /** * @param progress * 下载进度 */
        void onDownloading(int progress);
        /** * 下载失败 */
        void onDownloadFailed();
    }




}
