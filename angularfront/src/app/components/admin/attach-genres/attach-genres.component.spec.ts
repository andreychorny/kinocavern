import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttachGenresComponent } from './attach-genres.component';

describe('AttachGenresComponent', () => {
  let component: AttachGenresComponent;
  let fixture: ComponentFixture<AttachGenresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttachGenresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttachGenresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
