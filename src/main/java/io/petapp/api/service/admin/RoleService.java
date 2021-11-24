package io.petapp.api.service.admin;

import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.admin.RoleDTO;
import io.petapp.api.dto.user.InformationPetDTO;
import io.petapp.api.mapper.admin.RoleMapper;
import io.petapp.api.model.admin.Role;
import io.petapp.api.model.user.User;
import io.petapp.api.repository.admin.RoleRepository;
import io.petapp.api.repository.user.UserRepository;
import io.petapp.api.vm.admin.ManagedRoleVM;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    /**
     * create roles admin
     */
    public  RoleDTO  createRoles(ManagedRoleVM roleVM ) {

        try {
                Role  newRole = new Role();
                newRole.setCode(roleVM.getCode().trim());
                newRole.setName(roleVM.getName().trim());
                newRole.setDescription(roleVM.getDescription().trim());
                newRole.setActivated(roleVM.getActivated());
                newRole.setDeleted(false);
                Role role = roleRepository.save(newRole);
                return  RoleMapper.INSTANCE.toDTO(role);
        }
        catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    /**
     * Update roles admin
     */
    public  RoleDTO  updateRole(ManagedRoleVM roleVM ) {

        try {
            Role newRole = roleRepository.findByCode(roleVM.getCode().trim());

                newRole.setCode(roleVM.getCode().trim());
                newRole.setName(roleVM.getName().trim());
                newRole.setDescription(roleVM.getDescription().trim());
                newRole.setActivated(roleVM.getActivated());
                newRole.setDeleted(false);
                Role role = roleRepository.save(newRole);
                return  RoleMapper.INSTANCE.toDTO(role);
        }
        catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    /**
     * Get Role with param id
     * @param id
     * @return
     */
    public RoleDTO getRoleById(String id){
//        User user = userRepository.findByUserNameAndDeleted(userName, false);
//        if (user == null) {
//            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
//        }
        Role role = roleRepository.findByIdAndDeleted(id, false);

        if(role == null){
            throw new BusinessException(ExceptionType.NOT_FOUND_ROLE);
        }
        modelMapper = new ModelMapper();

        return modelMapper.map(role, RoleDTO.class);

    }

    public Page<RoleDTO> getLisRoles( Integer page, Integer size) {
//        User user = userRepository.findByUserNameAndDeleted(userName, false);
//        if (user == null) {
//            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
//        }
        modelMapper = new ModelMapper();
        return roleRepository.findAllByDeletedOrderByCreatedDateDesc(false, PageRequest.of(page, size))
            .map(role -> modelMapper.map(role, RoleDTO.class));
    }

    public Page<RoleDTO> getLisRolesWithParamSearch( Integer page, Integer size,  ManagedRoleVM roleVM) {
//        User user = userRepository.findByUserNameAndDeleted(userName, false);
//        if (user == null) {
//            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
//        }
        modelMapper = new ModelMapper();
        String code = roleVM.getCode().trim();
        String name = roleVM.getName().trim();
        String description = roleVM.getDescription().trim();
        Boolean activated = roleVM.getActivated();

        return roleRepository.findAllByCodeLikeAndNameLikeAndDescriptionLikeAndActivatedAndDeletedOrderByCreatedDateDesc(code,name,description,activated, false, PageRequest.of(page, size))
            .map(role -> modelMapper.map(role, RoleDTO.class));
    }

    public Boolean findBycode(String code){
        Boolean checkCode = false;
        Role dataRole = roleRepository.findByCode(code.trim());
        if(dataRole != null){
            checkCode = true;
        }
       return checkCode;
    }

    public void deleteRole(String id, String userName) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Role role = roleRepository.findByIdAndUserIdAndDeleted(id, user.getId(), false);

        if (role == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_ROLE);
        }
        role.setDeleted(true);
        role.setDeletedBy(userName);
        role.setDeletedDate(Instant.now().toEpochMilli());
        roleRepository.save(role);
    }
}
