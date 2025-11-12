# Null Role Issue - Fix Documentation

## Problem
Users were experiencing a `NullPointerException` when trying to login:
```
Cannot invoke "com.example.entity.Role.name()" because the return value of "com.example.entity.User.getRole()" is null
```

## Root Cause
Existing users in the database had `NULL` values in the `role` column, and the code was trying to call `.name()` on a null role object.

## Solutions Implemented

### 1. User Entity (`User.java`)
- Added constructor that always initializes role to `USER`
- Added `@PostLoad` callback to fix null roles when entity is loaded from database
- Added custom `getRole()` method that ensures role is never null (defensive programming)
- Added `@Column(nullable = false)` to prevent new NULL values

### 2. UserServiceImpl (`UserServiceImpl.java`)
- **register()**: Ensures role is set to USER if not provided in DTO
- **login()**: Checks for null role, sets to USER if null, and saves to database

### 3. CustomUserDetailsService (`CustomUserDetailsService.java`)
- Checks for null role before accessing it
- Auto-fixes null roles by setting to USER and saving to database

### 4. UserController (`UserController.java`)
- Added defensive null check before accessing role
- Auto-fixes and saves user if role is null
- Uses safe role name access with fallback

### 5. SecurityConfig (`SecurityConfig.java`)
- Removed task endpoints (as per requirement)
- Only Auth and User controllers are exposed

## Database Fix

If you have existing users with NULL roles in the database, run this SQL script:

```sql
UPDATE users SET role = 'USER' WHERE role IS NULL;
```

The SQL script is available at: `src/main/resources/fix-null-roles.sql`

## How It Works

1. **New Users**: Always get `USER` role by default (via constructor)
2. **Existing Users with NULL Role**: 
   - When loaded from database, `@PostLoad` fixes it in memory
   - Custom `getRole()` ensures it's never null when accessed
   - Service methods detect null and persist the fix to database
3. **Defensive Programming**: Multiple layers of protection ensure role is never null

## Testing

1. **Test Login**: Try logging in with existing users - should work without errors
2. **Test Registration**: Register a new user - should automatically get USER role
3. **Test Profile**: Access user profile - should show role without errors

## Controllers

Only two controllers are kept as requested:
- **AuthController**: `/api/v1/auth` - Register and Login
- **UserController**: `/api/v1/users` - User profile

## Next Steps

1. Restart the backend application
2. (Optional) Run the SQL script to fix existing NULL roles in database
3. Test login functionality
4. The application will automatically fix any NULL roles when users log in

## Notes

- The custom `getRole()` method overrides Lombok's generated getter
- Multiple safety checks ensure the role is never null at any point
- The fix is backward compatible - existing users will be automatically updated

