package com.htb_kg.ctf.dto.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterRequest {
    private Boolean hideSolved;
    private Boolean showBookMarked;
    private Boolean showAssigned;
}
