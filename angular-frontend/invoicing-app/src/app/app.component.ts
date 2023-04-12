import { Component, OnInit } from '@angular/core';
import { Company } from './company';
import { CompanyService } from './company.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'invoicing-app';
  companies: Company[] = [];
    newCompany: Company = new Company(0, "", "", "", 0,0)
  constructor(
          private companiesService: CompanyService
      ) {
      }

      ngOnInit(): void {
          this.companiesService.getCompanies()
              .subscribe(companies => {
                  this.companies = companies;
              });
      }


//
//   companies: Company[]  = [
//     new Company(
//       "taxId",
//       "address",
//       "name",
//        234,
//        2344
//     ),
//     new Company(
//           "taxId2",
//           "address2",
//           "name2",
//            2341,
//            23442
//         )
//   ]


  addCompany(){
//     this.companies.push(this.newCompany)
//     this.newCompany = new Company(0, "", "", "", 0, 0)
    this.companiesService.addCompany(this.newCompany)
                .subscribe(id => {
                    this.newCompany.id = id;
                    this.companies.push(this.newCompany);

                    this.newCompany = new Company(0, "", "", "", 0, 0);
                });
  }

  deleteCompany(companyToDelete: Company){
//     this.companies = this.companies.filter(company => company !== companyToDelete)
     this.companiesService.deleteCompany(companyToDelete.id)
                .subscribe(() => {
                    this.companies = this.companies.filter(company => company !== companyToDelete);
                })
  }
}
