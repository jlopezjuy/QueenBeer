export interface IUserProfile {
    id?: number;
    avatarContentType?: string;
    avatar?: any;
    phone?: string;
    mobilePhone?: string;
    userLogin?: string;
    userId?: number;
}

export class UserProfile implements IUserProfile {
    constructor(
        public id?: number,
        public avatarContentType?: string,
        public avatar?: any,
        public phone?: string,
        public mobilePhone?: string,
        public userLogin?: string,
        public userId?: number
    ) {}
}
