package com.hoaxify.ws.user.dto;

import com.hoaxify.ws.user.validation.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdate
  (@Size(min = 4,max = 255)
  @NotBlank(message = "{hoaxify.constraint.username.notblank}")
  String username,
   @FileType(types = {"jpeg","png"})
   String image
  ){

}
