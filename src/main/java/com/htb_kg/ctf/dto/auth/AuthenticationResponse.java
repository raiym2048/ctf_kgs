package com.htb_kg.ctf.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htb_kg.ctf.dto.hacker.HackerResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private UserResponse user;
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
}
