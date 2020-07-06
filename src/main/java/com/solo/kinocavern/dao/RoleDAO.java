package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.EnumRole;
import com.solo.kinocavern.entity.Role;

public interface RoleDAO {
    Role findByName(EnumRole name);
}
