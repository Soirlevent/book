package com.ruoyi.tools.repository;

import com.ruoyi.tools.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author
 * @date
 */
public interface EmailRepository extends JpaRepository<EmailConfig,Long> {
}
