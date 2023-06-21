import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ExerciseDTO } from 'src/app/models/exercise-dto';
import { FoodDTO } from 'src/app/models/food-dto';
import { TableExerciseResource } from 'src/app/models/table_exercise_resource';
import { TableResource } from 'src/app/models/table_resource';
import { UserDTO } from 'src/app/models/user-dto';
import { ExerciseService } from 'src/app/services/exercise-service';
import { FoodService } from 'src/app/services/food-service';
import { UserService } from 'src/app/services/user-service';

@Component({
  selector: 'app-meal-plan-mantain',
  templateUrl: './meal-plan-mantain.component.html',
  styleUrls: ['./meal-plan-mantain.component.css']
})
export class MealPlanMantainComponent implements OnInit {
  foods: FoodDTO[] = [];
  exercises: ExerciseDTO[] = [];
  selectedBreakfast: string = '';
  selectedLunch: string = '';
  selectedDinner: string = '';
  selectedExercise: string = '';

  totalCalories: number = 0;
  quantity: number = 0;


  breakfastTotalCalories: number = 0;
  lunchTotalCalories: number = 0;
  dinnerTotalCalories: number = 0;
  exerciseTotalCalories: number = 0;

  foodSelectedForBreakfast: TableResource[] = [];
  foodSelectedForLunch: TableResource[] = [];
  foodSelectedForDinner: TableResource[] = [];

  exercisesSelected: TableExerciseResource[] = [];

  constructor(private foodService: FoodService, private exerciseService: ExerciseService, private userService: UserService, private titlseService: Title) {
    this.calculateTotalCalories();
  }

  ngOnInit(): void {
    this.titlseService.setTitle('Here is your plan to mantain weight')
    this.getFoods();
    this.getExercises();
    const userIdString = localStorage.getItem('userId');
    const userIdBun = parseInt(userIdString);
    console.log(userIdBun)

    this.calculateTotalCalories();
    this.userService.getAllUserExercises().forEach(userExercise => {
      userExercise.forEach(uE => {
        if (uE.user.id === userIdBun) {
          const table_exercise_resource: TableExerciseResource = {
            exerciseDTO: uE.exercise,
            totalCalories: uE.repsOrTime * uE.exercise.calories
          }
          this.exercisesSelected.push(table_exercise_resource);
          this.exerciseTotalCalories += uE.repsOrTime * uE.exercise.calories;
        }
      })
    })

    this.userService.getAllUserFoods().forEach(userFood => {
      console.log(userFood);
      userFood.forEach(uF => {
        if (uF.additionalColumn === "breakfast" && uF.user.id === userIdBun) {
          console.log("mai departe");
          console.log(uF.food);
          const table_resource: TableResource = {
            foodDTO: uF.food,
            totalCalories: uF.quantityInGrams * uF.food.calories / 100
          }
          this.foodSelectedForBreakfast.push(table_resource);
          this.breakfastTotalCalories += uF.quantityInGrams * uF.food.calories / 100;
        }
        if (uF.additionalColumn === 'lunch' && uF.user.id === userIdBun) {
          const table_resource: TableResource = {
            foodDTO: uF.food,
            totalCalories: uF.quantityInGrams * uF.food.calories / 100
          }
          this.foodSelectedForLunch.push(table_resource);
          this.lunchTotalCalories += uF.quantityInGrams * uF.food.calories / 100;
        }
        if (uF.additionalColumn === 'dinner' && uF.user.id === userIdBun) {
          const table_resource: TableResource = {
            foodDTO: uF.food,
            totalCalories: uF.quantityInGrams * uF.food.calories / 100
          }
          this.foodSelectedForDinner.push(table_resource);
          this.dinnerTotalCalories += uF.quantityInGrams * uF.food.calories / 100;
        }
      })
    });
  }

  getFoods(): void {
    this.foodService.getAllFoods().subscribe(foods => {
      this.foods = foods;
    });
  }

  getExercises(): void {
    this.exerciseService.getAllExercises().subscribe(exercises => {
      this.exercises = exercises;
    });
  }

  addFoodToMeal(mealType: string, quantity: number): void {
    if (mealType === 'breakfast') {
      this.foodService.getFoodByName(this.selectedBreakfast).subscribe(food => {
        console.log(food.id)
        const userIdString = localStorage.getItem('userId');
        const userIdBun = parseInt(userIdString);
        console.log(userIdBun)
        console.log(mealType)
        if (quantity) {
          this.userService.addFoodToUser(userIdBun, food.id, mealType, quantity)
          const table_resource: TableResource = {
            foodDTO: food,
            totalCalories: quantity * food.calories / 100
          }
          this.breakfastTotalCalories += quantity * food.calories / 100;
          this.foodSelectedForBreakfast.push(table_resource); //se adauga mancarea in lista foodSelectedForBreakfast
        } else {
          this.userService.addFoodToUser(userIdBun, food.id, mealType, 100)
          const table_resource: TableResource = {
            foodDTO: food,
            totalCalories: 100 * food.calories / 100
          }
          this.breakfastTotalCalories += 100 * food.calories / 100;
          this.foodSelectedForBreakfast.push(table_resource);
        }

        this.calculateTotalCalories();
      });
    }
    if (mealType === 'lunch') {
      this.foodService.getFoodByName(this.selectedLunch).subscribe(food => {
        const userIdString = localStorage.getItem('userId');
        const userIdBun = parseInt(userIdString);
        if (quantity) {
          this.userService.addFoodToUser(userIdBun, food.id, mealType, quantity)
          const table_resource: TableResource = {
            foodDTO: food,
            totalCalories: quantity * food.calories / 100
          }
          this.lunchTotalCalories += quantity * food.calories / 100;
          this.foodSelectedForLunch.push(table_resource);
        } else {
          this.userService.addFoodToUser(userIdBun, food.id, mealType, 100)
          const table_resource: TableResource = {
            foodDTO: food,
            totalCalories: 100 * food.calories / 100
          }
          this.lunchTotalCalories += 100 * food.calories / 100;
          this.foodSelectedForLunch.push(table_resource);
        }
        this.calculateTotalCalories();
      });
    }
    if (mealType === 'dinner') {
      this.foodService.getFoodByName(this.selectedDinner).subscribe(food => {
        const userIdString = localStorage.getItem('userId');
        const userIdBun = parseInt(userIdString);
        if (quantity) {
          this.userService.addFoodToUser(userIdBun, food.id, mealType, quantity)
          const table_resource: TableResource = {
            foodDTO: food,
            totalCalories: quantity * food.calories / 100
          }
          this.dinnerTotalCalories += quantity * food.calories / 100;
          this.foodSelectedForDinner.push(table_resource);
        } else {
          this.userService.addFoodToUser(userIdBun, food.id, mealType, 100)
          const table_resource: TableResource = {
            foodDTO: food,
            totalCalories: 100 * food.calories / 100
          }
          this.dinnerTotalCalories += 100 * food.calories / 100;
          this.foodSelectedForDinner.push(table_resource);
        }
        this.calculateTotalCalories();
      });
    }
  }

  addExercise(repsOrTime: number): void {
    const userIdString = localStorage.getItem('userId');
    const userIdBun = parseInt(userIdString);
    this.exerciseService.getExerciseByName(this.selectedExercise).subscribe(exercise => {
      console.log(exercise.name);
      if (repsOrTime) {
        const table_exercise_resource: TableExerciseResource = {
          exerciseDTO: exercise,
          totalCalories: exercise.calories * repsOrTime
        }
        this.userService.addExerciseToUser(userIdBun, exercise.id, repsOrTime)
        this.exercisesSelected.push(table_exercise_resource);
        this.exerciseTotalCalories += repsOrTime * exercise.calories;
      } else {
        const table_exercise_resource: TableExerciseResource = {
          exerciseDTO: exercise,
          totalCalories: exercise.calories * 1
        }
        this.userService.addExerciseToUser(userIdBun, exercise.id, 1)
        this.exercisesSelected.push(table_exercise_resource);
        this.exerciseTotalCalories += 1 * exercise.calories;
      }
      this.calculateTotalCalories();
    })
  }

  async getUserData(): Promise<UserDTO> {
    const userIdString = localStorage.getItem('userId');
    const userIdBun = parseInt(userIdString);
    return await this.userService.findByIdSynchronous(userIdBun);
  }

  calculateTotalCalories(): void {

    const userIdString = localStorage.getItem('userId');
    const userIdBun = parseInt(userIdString);
    const user = this.getUserData()

    user.then((userDTO: UserDTO) => {
      this.totalCalories = userDTO.calories;
      console.log(this.totalCalories)
    },
      (error: any) => {
        console.error('error getting user total calories');
      })
  }

  // calculateTotalCalories(): void {
  //   const userIdString = localStorage.getItem('userId');
  //   const userIdBun = parseInt(userIdString);

  //   const user = this.userService.findById(userIdBun);

  //   user.subscribe(u => {
  //     this.totalCalories = u.calories;
  //   })
  // }

  clearRecords(): void {
    const userIdString = localStorage.getItem('userId');
    const userIdBun = parseInt(userIdString);
    this.foodSelectedForBreakfast = [];
    this.foodSelectedForDinner = [];
    this.foodSelectedForLunch = [];
    this.exercisesSelected = [];
    this.totalCalories = 0;
    this.breakfastTotalCalories = 0;
    this.lunchTotalCalories = 0;
    this.dinnerTotalCalories = 0;
    this.userService.clearRecords(userIdBun);
  }

  showPrompt() {
    alert('The calorie number is per 100g, enter the approximative quantity of selected item. Leaving this field, by default, it means 100g of selected item.');
  }
}
