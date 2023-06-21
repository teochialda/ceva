import { ExerciseDTO } from "./exercise-dto";
import { UserDTO } from "./user-dto";

export class UserExerciseDTO {
    id: number;
    user: UserDTO;
    exercise: ExerciseDTO;
    repsOrTime: number;
}