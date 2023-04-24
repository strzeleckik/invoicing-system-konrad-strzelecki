import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { CompanyService } from './company.service';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { Company } from './company';

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let component: AppComponent;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        { provide: CompanyService, useClass: MockCompanyService }
      ],
      declarations: [
        AppComponent
      ],
      imports: [
        FormsModule
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;

    component.ngOnInit()
    fixture.detectChanges();
  });

  it('should display a list of companies', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    component.ngOnInit()
    fixture.detectChanges();
    expect(fixture.nativeElement.innerText).toContain("111-111-11-11	ul. First 1	First Ltd.	111.11	1111.11")
    expect(fixture.nativeElement.innerText).toContain("222-222-22-22	ul. Second 2	Second Ltd.	222.22	2222.22")
    expect(component.companies.length).toBe(2)
    expect(component.companies).toBe(MockCompanyService.companies)
  });

  it(`newly added company is added to the list`, () => {
        const taxIdInput: HTMLInputElement = fixture.nativeElement.querySelector("input[name=taxIdentificationNumber]")
        taxIdInput.value = "333-333-33-33"
        taxIdInput.dispatchEvent(new Event('input'));
      const nameInput: HTMLInputElement = fixture.nativeElement.querySelector("input[name=name]")
      nameInput.value = "Third Ltd."
      nameInput.dispatchEvent(new Event('input'));

      const addressInput: HTMLInputElement = fixture.nativeElement.querySelector("input[name=address]")
      addressInput.value = "ul. Third 3"
      addressInput.dispatchEvent(new Event('input'));

      const addInvoiceBtn: HTMLElement = fixture.nativeElement.querySelector("#addCompanyBtn")
      addInvoiceBtn.click()

      fixture.detectChanges();

      expect(fixture.nativeElement.innerText).toContain("333-333-33-33	ul. Third 3	Third Ltd.	0	0")
  });


  class MockCompanyService {
    static companies: Company[] = [
          new Company(
            1,
            "111-111-11-11",
            "ul. First 1",
            "First Ltd.",
            1111.11,
            111.11
          ),
          new Company(
            2,
            "222-222-22-22",
            "ul. Second 2",
            "Second Ltd.",
            2222.22,
            222.22
          )
        ];

         getCompanies() {
              return of(MockCompanyService.companies)
            }

            addCompany(company: Company) {
              MockCompanyService.companies.push(company)
              return of()
            }

  }
});
