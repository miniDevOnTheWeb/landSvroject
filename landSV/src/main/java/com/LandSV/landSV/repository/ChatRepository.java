package com.LandSV.landSV.repository;

import com.LandSV.landSV.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    @Query("select c from Chat c " +
            "where (c.creator.id = :creatorId and c.invited.id = :invitedId) " +
            "or (c.creator.id = :invitedId and c.invited.id = :creatorId)")
    Optional<Chat> findByCreatorIdOrInvitedId(@Param("creatorId") UUID creatorId, @Param("invitedId") UUID invitedId);

    @Query("select count(c) > 0 from Chat c " +
            "where (c.creator.id = :creatorId and c.invited.id = :invitedId) " +
            "or (c.creator.id = :invitedId and c.invited.id = :creatorId)")
    boolean existsByCreatorIdOrInvitedId(@Param("creatorId") UUID creatorId, @Param("invitedId") UUID invitedId);

    List<Chat> findChatsByCreatorIdOrInvitedIdOrderByLastMessageDesc(UUID creatorId, UUID invitedId);
}
