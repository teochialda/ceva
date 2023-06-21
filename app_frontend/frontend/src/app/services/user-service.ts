import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from '../models/user-dto';
import { FoodDTO } from '../models/food-dto';
import { ExerciseDTO } from '../models/exercise-dto';
import { UserFoodDTO } from '../models/user-food-dto';
import { UserExerciseDTO } from '../models/user-exercise-dto';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl   = 'http://localhost:8080/users';

    private userFoodsUrl = 'http://localhost:8080/userfoods';
    private userExercisesUrl = 'http://localhost:8080/userexercises';

    private clearAllRecordsUrl = 'clearAllRecords'

    constructor(private httpClient: HttpClient) { }

    private userId: number | null = null;

    setUserId(userId: number): void {
        this.userId = userId;
    }

    getUserId(): number | null {
        return this.userId;
    }

    getUserCredentials(name: string, password: string): Observable<UserDTO> {
        const url = `${this.apiUrl}/login/${name}/${password}`;
        return this.httpClient.get<UserDTO>(url);
    }

    insertUser(user: UserDTO): Observable<UserDTO> {
        return this.httpClient.post<UserDTO>(this.apiUrl, user);
    }

    addFoodToUser(userId: number, foodId: number, momentOfTheDay: string, quantityInGrams: number): void {
        const url = `${this.apiUrl}/${userId}/foods/${foodId}/${momentOfTheDay}/${quantityInGrams}`;
        this.httpClient.post(url, {}).subscribe(
          () => {
            console.log('Food added to user successfully.');
            
          },
          (error) => {
            console.error('Failed to add food to user:', error);
            console.log(userId);
            console.log(foodId);
          }
        );
      }

      addExerciseToUser(userId: number, exerciseId: number, repsOrTime: number): void {
        const url = `${this.apiUrl}/${userId}/exercises/${exerciseId}/${repsOrTime}`;
        this.httpClient.post(url, {}).subscribe(
          () => {
            console.log('Exercise added to user successfully.');
            
          },
          (error) => {
            console.error('Failed to add exercise to user:', error);
            console.log(userId);
            console.log(exerciseId);
          }
        );
      }

    findById(userId: number): Observable<UserDTO> {
        const url = `${this.apiUrl}/${userId}`
        return this.httpClient.get<UserDTO>(url);
    }

    findByIdSynchronous(userId: number): Promise<UserDTO> {
        const url = `${this.apiUrl}/${userId}`
        return this.httpClient.get<UserDTO>(url).toPromise();
    }

    getUserFoods(userId: number): Observable<FoodDTO[]> {
      const url = `${this.apiUrl}/${userId}/get-foods`;
      return this.httpClient.get<FoodDTO[]>(url);
    }

    getUserExercises(userId: number): Observable<ExerciseDTO[]> {
      const url = `${this.apiUrl}/${userId}/get-exercises`;
      return this.httpClient.get<ExerciseDTO[]>(url);
    }

    getAllUserFoods(): Observable<UserFoodDTO[]> {
      return this.httpClient.get<UserFoodDTO[]>(this.userFoodsUrl);
    }

    getAllUserExercises(): Observable<UserExerciseDTO[]> {
      return this.httpClient.get<UserExerciseDTO[]>(this.userExercisesUrl);
    }

    clearRecords(userId: number): void {
      const url = `${this.apiUrl}/clearAllRecords/${userId}`
      this.httpClient.post(url, {}).subscribe(
        () => {
          console.log('Records cleared');
        }
        // },
        // (error) => {
        //   console.error('Failed to clear records:', error);
        // }
      );
    }

}