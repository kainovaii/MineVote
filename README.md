<p align="center">
    <br />
    <img src="https://i.postimg.cc/C1C5nLFR/favicon.png" width="30%">
    <br />
</p>
<p align="center">
    <img src="https://img.shields.io/badge/Version-0.8-orange.svg" />
    <img style="margin-left: 10px;" src="https://img.shields.io/badge/License-MIT-orange.svg" />
</p>

## 📥 Installation

1. **Download** the `MineVote.jar` file (link to be added).
2. **Place** the `.jar` file into your server’s `plugins/` folder.
3. **Make sure** the following plugins are also installed in the `plugins/` folder:
   - `PlaceholderAPI`
   - `NuVotifier`
4. **Start** or **restart** your server.
5. A `MineVote/` folder will be generated with the configuration file.

## ⚙️ NuVotifier Configuration

To ensure that votes are properly received by your server, configure NuVotifier:

1. Open the file:  
   `plugins/NuVotifier/config.yml`

2. Check or adjust the following fields:

```yaml
host: 0.0.0.0
port: 8192
token: "<automatically generated>"
```

## 🧭 Commands (`/minevote` or `/vote`)

MineVote provides a set of in-game commands to manage and interact with the voting system.

| Command                       | Permission              | Description                                                                 |
|-------------------------------|--------------------------|-----------------------------------------------------------------------------|
| `/vote`                       | —                        | Opens the main GUI.                                                         |
| `/vote help`                  | —                        | Displays the list of available commands.                                    |
| `/vote info`                  | —                        | Shows plugin information (author, version, dependencies, etc.).             |
| `/vote shop`                  | —                        | Opens the vote shop GUI.                                                    |
| `/vote site`                  | —                        | Opens the vote sites GUI.                                                   |
| `/vote ranking`               | —                        | Opens the vote ranking GUI.                                                 |
| `/vote give <player> <value>` | `minevote.give`          | Gives `<value>` extra votes to `<player>`.                                  |
| `/vote boost <on/off>`        | `minevote.boost`         | Manually starts or stops the vote boost.                                    |
| `/vote reload`                | `minevote.reload`        | Reloads the plugin configuration.                                           |

### Notes

- Use `/vote help` in-game to see a help message.
- Commands requiring permissions should be granted via your permissions plugin (e.g., LuckPerms).


## 🔧 Placeholders (PlaceholderAPI)

MineVote provides several placeholders to display vote boost information on your server using PlaceholderAPI.

| Placeholder              | Description                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| `%minevote_counter%`     | Total number of votes counted during the boost.                            |
| `%minevote_objective%`   | Vote goal set in the configuration.                                        |
| `%minevote_status%`      | Boost status: displays a message indicating whether the boost is active.   |
| `%minevote_timeleft%`    | Remaining boost time in `mm:ss` format, or a clock icon if it has ended.   |

### How to use?

1. Make sure PlaceholderAPI is installed and loaded on your server.
2. Integrate these placeholders into your messages, signs, or commands that support PlaceholderAPI.
