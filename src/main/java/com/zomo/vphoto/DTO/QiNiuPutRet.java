package com.zomo.vphoto.DTO;

import lombok.Data;

@Data
public final class QiNiuPutRet {
    private String key;
    private String hash;
    private String bucket;
    private Integer width;
    private Integer height;
    private Integer fsize;

}
