package me.idriz.cleddit.repository.sql;

import java.util.UUID;
import me.idriz.cleddit.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

}
