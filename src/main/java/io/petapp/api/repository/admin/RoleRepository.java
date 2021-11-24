package io.petapp.api.repository.admin;

import io.petapp.api.dto.admin.RoleDTO;
import io.petapp.api.model.admin.Role;
import io.petapp.api.model.user.Pet;
import io.petapp.api.model.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

   Role findByCode(String code);

   Role findByIdAndUserIdAndDeleted(String id,String UserId, Boolean deleted);

   Role findByIdAndDeleted(String id, Boolean deleted);

   Page<Role> findAllByDeletedOrderByCreatedDateDesc(Boolean deleted, Pageable pageable);

   Page<Role> findAllByCodeLikeAndNameLikeAndDescriptionLikeAndActivatedAndDeletedOrderByCreatedDateDesc(String code, String name, String Description ,Boolean activated, Boolean deleted, Pageable pageable);

}
