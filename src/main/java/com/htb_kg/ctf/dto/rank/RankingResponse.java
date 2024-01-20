package com.htb_kg.ctf.dto.rank;

import com.htb_kg.ctf.dto.file.FileResponse;
import com.htb_kg.ctf.enums.Role;
import lombok.Data;

@Data
public class RankingResponse {

    private Long userId;
    private FileResponse logo_image;
    private String nickname;
    private Role role;
    private Integer points;
}
