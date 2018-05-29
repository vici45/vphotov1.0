create table `role`(
`id` int(3) not null AUTO_INCREMENT,
`role_name` varchar(32) not null comment '角色名称',
PRIMARY key (`id`)
)comment '角色表'

create table `project`(
`id` int(11) not null AUTO_INCREMENT,
`project_name` varchar(256) not null comment '项目名称',
`project_sit` varchar(256) not null comment '项目地点',
`project_time` timestamp not null comment'项目时间',
`project_banner_host` varchar(256) comment 'banner地址',
`project_key_image_host` varchar(256) comment '主图地址',
`project_content` varchar(256) comment '活动内容',
`project_content_detail` varchar(1024) comment '活动内容详情',
`project_create_user_id` int(11) not null comment '创建活动用户id',
`project_create_time` timestamp default current_timestamp comment '创建活动时间',
`project_update_time` timestamp default current_timestamp on update current_timestamp  comment '活动最后修改时间',
`project_status` int(3) not null comment '活动状态',
 primary key(`id`)
)comment '项目表'

create table `project_detail`(
`id` int(11) not null AUTO_INCREMENT,
`project_id` int(11) not null comment'项目Id',
`create_time` timestamp default current_timestamp comment'图片上传时间',
`image_host` varchar(256) not null comment'图片地址',
PRIMARY key(`id`)
)comment '项目详情表'

create table `project_privilege`(
`id` int(11) not null AUTO_INCREMENT,
`project_id` int(11) not null comment '项目id',
`user_id` int(11) not null comment'用户id',
PRIMARY key(`id`) 
) comment '项目权限表'