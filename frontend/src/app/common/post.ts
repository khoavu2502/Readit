import { Category } from "./category";
import { User } from "./user";

export class Post {
    constructor(public id: number,
                public title: string,
                public thumbnail: string,
                public content: string,
                public published: boolean,
                public createdAt: Date,
                public updatedAt: Date,
                public publishedAt: Date,
                public user: User,
                public categories: Category[]) {}
}
