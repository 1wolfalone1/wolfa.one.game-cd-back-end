terraform {
  cloud {
    organization = "thientryhard"

    workspaces {
      name = "example-workspace"
    }
  }
}