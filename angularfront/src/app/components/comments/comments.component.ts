import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { CommentService } from 'src/app/services/comment.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  @Input()
  comments;

  @Input()
  moviedId: number;

  commentContent: string;

  constructor(private userService: UserService, 
              private commentService: CommentService) { }

  ngOnInit(): void {
    console.log(this.comments);
  }


  getChildComments(parentId: number): any[]{
    const childComments = this.comments.filter(comment => comment.parentId === parentId);
    console.log(childComments);
    return childComments;
  }

  onButtonPostComment(parentId: number, replyContent: string){
    if (parentId != null){
      this.commentContent = replyContent;
    }
    this.userService.postComment(this.moviedId, this.commentContent, parentId).subscribe(
        () => {
            window.location.reload();
        }
    );
  }

  onDelete(commentId: number){
    if (confirm("Are you sure to delete comment?")){
      this.commentService.deleteComment(commentId).subscribe();
    }
  }
}
