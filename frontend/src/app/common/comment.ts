import { User } from "./user";

export class Comment {
    constructor(public id: number,
                public content: string,
                public createdAt: Date,
                public user: User) { }
}
