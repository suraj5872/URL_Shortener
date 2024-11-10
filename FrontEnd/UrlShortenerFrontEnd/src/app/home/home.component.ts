import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  url = {
    longUrl: '',           // for creating a new short URL
    shortCode: '',         // for form submission related to short code creation
    shortCodeRetrieve: '', // for retrieving URL based on short code
    newUrl: '',            // for updating a URL
    deleteShortCode: '',   // for deleting short URL
    statsShortCode: ''     // for fetching URL statistics
  };
  shortUrl: any;
  originalUrl: any;
  urlStats: any;

  constructor(private http: HttpClient) {}
  ngOnInit(): void {
    console.log("ngOnInIt is Called")
   this.getData();
  }
  getData() {
    console.log('getData method is called');
  }
   // Method to create short URL
   createShortUrl() {
    this.http.post('http://localhost:9090/api/shorten', { url: this.url.longUrl }).subscribe((data: any) => {
      this.shortUrl = data;
      this.url.longUrl = ''; // Reset the long URL input after submission
    });
  }

  // Method to retrieve original URL by short code
  getOriginalUrl() {
    this.http.get(`http://localhost:9090/api/shorten/${this.url.shortCodeRetrieve}`).subscribe((data: any) => {
      this.originalUrl = data;
      this.url.shortCodeRetrieve = ''; // Reset the short code input after retrieval
    });
  }

  // Method to update short URL
  updateShortUrl() {
    this.http.put(`http://localhost:9090/api/shorten/${this.url.shortCode}`, { url: this.url.newUrl }).subscribe((data: any) => {
      this.shortUrl = data;
      this.url.newUrl = ''; // Reset the new URL input after update
    });
  }

  // Method to delete short URL
  deleteShortUrl() {
    this.http.delete(`http://localhost:9090/api/shorten/${this.url.deleteShortCode}`).subscribe(() => {
      alert('Short URL deleted');
      this.url.deleteShortCode = ''; // Reset the delete short code input after deletion
    });
  }

  // Method to get statistics
  getStatistics() {
    this.http.get(`http://localhost:9090/api/shorten/${this.url.statsShortCode}/stats`).subscribe((data: any) => {
      this.urlStats = data;
      this.url.statsShortCode = ''; // Reset the stats short code input after fetching stats
    });
  }
}
