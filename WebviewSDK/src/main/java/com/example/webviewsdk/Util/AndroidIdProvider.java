
package com.example.webviewsdk.Util;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MediaCodecInfoCC {
    private String name;
    List<String> capabilities;

    MediaCodecInfoCC(String name, List<String> capabilities) {
        this.name = name;
        this.capabilities = capabilities;
    }

    public interface CodecInfoProvider {
        public List<MediaCodecInfo> codecsList();
    }

    class CodecInfoProviderImpl implements CodecInfoProvider {
        private MediaCodecList codecList;

        @Override
        public List<MediaCodecInfo> codecsList() {
            return extractCodecInfo();
        }

        private List<MediaCodecInfo> extractCodecInfo() {
            List<MediaCodecInfo> mediaCodecInfoList = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= 21) {
                MediaCodecList mediaCodecList = new MediaCodecList(MediaCodecList.ALL_CODECS);
                MediaCodecInfo[] mediaCodecInfos = mediaCodecList.getCodecInfos();
                mediaCodecInfoList.addAll(Arrays.asList(mediaCodecInfos));
            } else {
                int count = MediaCodecList.getCodecCount();
                for (int i = 0; i < count; i++) {
                    MediaCodecInfo mci = MediaCodecList.getCodecInfoAt(i);
                    mediaCodecInfoList.add(mci);
                    MediaCodecInfoCC ddd = new MediaCodecInfoCC(mci.getName(),Arrays.asList(mci.getSupportedTypes()));
                }

            }
            return mediaCodecInfoList;
        }
    }
}