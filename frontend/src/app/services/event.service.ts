import {inject, Injectable, NgZone} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface EventItem {
  id?: string;
  title: string;
  description: string;
  icon?: string;
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private http = inject(HttpClient);
  private zone = inject(NgZone);
  private apiUrl = 'http://localhost:8080/api';

  getEvents(): Observable<EventItem[]> {
    return this.http.get<EventItem[]>(`${this.apiUrl}/events`);
  }

  getEventStream(): Observable<EventItem> {
    return new Observable(observer => {
      const eventSource = new EventSource(`${this.apiUrl}/events/stream`);

      eventSource.onmessage = (event) => {
        this.zone.run(() => {
          observer.next(JSON.parse(event.data));
        });
      };

      eventSource.onerror = (error) => {
        this.zone.run(() => observer.error(error));
      };

      return () => eventSource.close()
    })
  }
}
