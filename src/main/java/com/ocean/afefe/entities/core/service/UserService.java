package com.ocean.afefe.entities.core.service;

import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserProfile;

public interface UserService {
    UserProfile getOrCreateBasicUserProfile(User user);

    OrgMember registerUserInOrganization(User user, Organization organization);
}
