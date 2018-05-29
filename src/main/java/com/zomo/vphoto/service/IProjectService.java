package com.zomo.vphoto.service;

import com.zomo.vphoto.common.ServiceResponse;

public interface IProjectService {
    ServiceResponse findAllProject();
    ServiceResponse findProjectByUserId();
}
