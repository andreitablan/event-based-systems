# [Sisteme bazate pe evenimente - Tema](https://edu.info.uaic.ro/sisteme-bazate-pe-evenimente/eval.html)

## Descriere
Scrieti un program care sa genereze aleator seturi echilibrate de subscriptii si publicatii cu posibilitatea de fixare a: numarului total de mesaje (publicatii, respectiv subscriptii), ponderii pe frecventa campurilor din subscriptii si ponderii operatorilor de egalitate din subscriptii pentru cel putin un camp. Publicatiile vor avea o structura fixa de campuri. Implementarea temei va include o posibilitate de paralelizare pentru eficientizarea generarii subscriptiilor si publicatiilor, si o evaluare a timpilor obtinuti.  
  
Exemplu:  
_Publicatie:_ {(company,"Google");(value,90.0);(drop,10.0);(variation,0.73);(date,2.02.2022)} - Structura fixa a campurilor publicatiei e: company-string, value-double, drop-double, variation-double, date-data; pentru anumite campuri (company, date), se pot folosi seturi de valori prestabilite de unde se va alege una la intamplare; pentru celelalte campuri se pot stabili limite inferioare si superioare intre care se va alege una la intamplare.  
  
_Subscriptie:_{(company,=,"Google");(value,>=,90);(variation,<,0.8)} - Unele campuri pot lipsi; frecventa campurilor prezente trebuie sa fie configurabila (ex. 90% company - 90% din subscriptiile generate trebuie sa includa campul "company"); pentru cel putin un camp (exemplu - company) trebui sa se poate configura un minim de frecventa pentru operatorul "=" (ex. macar 70% din subscriptiile generate sa aiba ca operator pe acest camp egalitatea).

## Tipul de Paralelizare: Threads

### Publicatie
#### Fără Paralelizare (1 thread)
- Numărul de Threads: 1
- Numărul de Mesaje Generate: 1.000.000
- Timpul de Execuție Mediu: ~300 milisecunde
- Specificațiile Procesorului: *Intel(R) Core(TM) i5-1035G1 CPU @ 1.00GHz   1.20 GHz*

#### Cu Paralelizare (4 thread-uri)
- Numărul de Threads: 4
- Numărul de Mesaje Generate: 1.000.000
- Timpul de Execuție Mediu: ~500 milisecunde
- Specificațiile Procesorului: *Intel(R) Core(TM) i5-1035G1 CPU @ 1.00GHz   1.20 GHz*
![image](https://github.com/andreitablan/event-based-systems/assets/76064833/4a4e03d5-f108-4443-bed6-a90b2bc44771)
### Subscriptie
#### Fără Paralelizare (1 thread)
- Numărul de Threads: 1
- Numărul de Mesaje Generate: 1.000.000
- Timpul de Execuție Mediu: ~8000 Milisecunde
- Specificațiile Procesorului: *Intel(R) Core(TM) i5-1035G1 CPU @ 1.00GHz   1.20 GHz*

#### Cu Paralelizare 
- Numărul de Threads: 
	- **numarul de subcriptii / 100**; daca numarul de subcriptii > 1000
	- **4**; daca numarul de subscriptii <=1000
- Numărul de Mesaje Generate: 1.000.000
- Timpul de Execuție Mediu: ~6000 Milisecunde
- Specificațiile Procesorului: *Intel(R) Core(TM) i5-1035G1 CPU @ 1.00GHz   1.20 GHz*
![image](https://github.com/andreitablan/event-based-systems/assets/76064833/4f45377d-727c-4a59-a6d2-0dc657f879ec)
## Autori
- [Fanaru Victor](https://github.com/FanaruVictor) 
- [Puscasu Bogdan](https://github.com/gundar10)
- [Tablan Andrei](https://github.com/andreitablan)
