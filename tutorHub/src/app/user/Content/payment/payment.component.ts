import { Component, OnInit } from '@angular/core';
import {map} from 'rxjs/operators';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {LoginService} from '../../../service/login/login.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {

  sum: number;
  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute, private loginService: LoginService) {
  }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.queryParamMap.get(`paymentId`) === null) {
      alert(`no parametrs`);
    } else {
      const params = new HttpParams().set('paymentId', this.activatedRoute.snapshot.queryParamMap.get(`paymentId`))
        .set('PayerID', this.activatedRoute.snapshot.queryParamMap.get(`PayerID`))
        .set('userID', this.loginService.getLoggedInUserName());
      alert(this.activatedRoute.snapshot.queryParamMap.get(`PayerID`))
      alert(JSON.stringify(params))
      this.http.get<JSON>('http://localhost:8080/paypal/complete/payment', {params})
        .subscribe(res =>
          alert(JSON.stringify(res))
        );
    }
  }


  public makePayment() {
    const url = 'http://localhost:8080/';
    // let items;
    this.http.post<JSON>(url + 'paypal/make/payment?sum=' + this.sum, {})
      .subscribe(res =>
        window.location.href = res['redirect_url']
      );
  }

  completePayment(paymentId, payerId) {
    const url = 'http://localhost:8080/';
    return this.http.post(url + 'paypal/complete/payment?paymentId=' + paymentId + '&payerId=' + payerId , {})
      .pipe(map((response: Response) => console.log(JSON.stringify(response))));
  }

}
