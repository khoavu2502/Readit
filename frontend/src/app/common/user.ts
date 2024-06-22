import { Post } from "./post";

export class User {
    constructor(public id: number,
                public username: string,
                public email: string,
                public avatar: string,
                public posts: Post[],
                public followers: number[],
                public following: number[]) { }
}
