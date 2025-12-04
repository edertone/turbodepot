package com.edertone.turbodepot_spring.model.dto;

/**
 * System version DTO.
 *
 * @param version   system version
 * @param gitCommit git commit
 * @param gitBranch git branch
 * @param buildTime git build time
 */
public record SystemVersionDto(
    String version,
    String gitCommit,
    String gitBranch,
    String buildTime
) {
}
