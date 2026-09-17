import {Component, inject, OnInit, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EventItem, EventService} from '../../services/event.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-timeline',
  imports: [CommonModule],
  templateUrl: './timeline.component.html',
  styleUrl: './timeline.component.scss'
})

export class TimelineComponent implements OnInit{
    private eventService = inject(EventService);
    private streamSub?: Subscription;

    events = signal<EventItem[]>([])

    ngOnInit(): void {
        this.eventService.getEvents().subscribe({
          next: (data) => this.events.set(data),
          error: (err) => console.error("Unable to load timeline events:", err)
        });

        this.streamSub = this.eventService.getEventStream().subscribe({
          next: (newEvent) => {
            this.events.update(current => [newEvent, ...current]);
          }
        })
    }

    ngOnDestroy() {
      this.streamSub?.unsubscribe();
    }

}
