import { Author } from './author';

export class Publication{
    id:number;
    title:string;
    type:string;
    attributes:string
    authors:Author[]=[]
}